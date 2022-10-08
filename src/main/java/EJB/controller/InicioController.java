package EJB.controller;


import EJB.CalendarioFacadeLocal;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import modelo.Calendario;
import modelo.Usuario;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

@Named
@ViewScoped
public class InicioController implements Serializable {

    @Inject
    private Calendario cal;
    
    @EJB
    private CalendarioFacadeLocal calendarioEJB;

    private ScheduleModel eventModel;

    private ScheduleModel lazyEventModel;

    private ScheduleEvent<?> event = new DefaultScheduleEvent<>();

    private boolean slotEventOverlap = true;
    private boolean showWeekNumbers = false;
    private boolean showHeader = true;
    private boolean draggable = true;
    private boolean resizable = true;
    private boolean showWeekends = true;
    private boolean tooltip = true;
    private boolean allDaySlot = true;
    private boolean rtl = false;

    private double aspectRatio = Double.MIN_VALUE;

    private String leftHeaderTemplate = "prev,next today";
    private String centerHeaderTemplate = "title";
    private String rightHeaderTemplate = "dayGridMonth,timeGridWeek,timeGridDay,listYear";
    private String nextDayThreshold = "09:00:00";
    private String weekNumberCalculation = "local";
    private String weekNumberCalculator = "date.getTime()";
    private String displayEventEnd;
    private String timeFormat;
    private String slotDuration = "00:30:00";
    private String slotLabelInterval;
    private String slotLabelFormat;
    private String scrollTime = "06:00:00";
    private String minTime = "04:00:00";
    private String maxTime = "20:00:00";
    private String locale = "es";
    private String timeZone = "";
    private String clientTimeZone = "local";
    private String columnHeaderFormat = "";
    private String view = "timeGridWeek";
    private String height = "auto";

    private String extenderCode = "// Write your code here or select an example from above";
    private String selectedCalendario = "";
    
    private List<Calendario> listaCalendarios;
    private Map<String, Calendario> eventMap;

    @PostConstruct
    public void init() {
        eventModel = new DefaultScheduleModel();
        eventMap = new HashMap<>();
        listaCalendarios = calendarioEJB.findAll();
        
        for(Calendario c : listaCalendarios) {
            DefaultScheduleEvent<?> event = DefaultScheduleEvent.builder()
                    .title(c.getTitulo())
                    .startDate(LocalDateTime.ofInstant(c.getFechaInicio().toInstant(), ZoneId.systemDefault()))
                    .endDate(LocalDateTime.ofInstant(c.getFechaFin().toInstant(), ZoneId.systemDefault()))
                    .description(c.getDescripcion())
                    .borderColor("#" + c.getParaUsuario().getColor()) 
                    .backgroundColor("#" + c.getParaUsuario().getColor())
                    .allDay(c.isTodoElDia())
                    .overlapAllowed(true)
                    .draggable(true)
                    .resizable(true)
                    .editable(true)
                    .build();
            eventModel.addEvent(event);
            eventMap.put(event.getId(), c);
        }
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public LocalDate getInitialDate() {
        return LocalDate.now().plusDays(1);
    }

    public ScheduleEvent<?> getEvent() {
        return event;
    }

    public void setEvent(ScheduleEvent<?> event) {
        this.event = event;
    }

    public void addEvent() {
        if (event.isAllDay()) {
            if (event.getStartDate().toLocalDate().equals(event.getEndDate().toLocalDate())) {
                event.setEndDate(event.getEndDate().plusDays(1));
            }
        }

        if (event.getId() == null) {
            eventModel.addEvent(event); //Se añade el evento al modelo
            //Se establecen los datos del nuevo evento
            cal.setTitulo(event.getTitle());
            cal.setDescripcion(event.getDescription());
            cal.setFechaInicio(Date.from(event.getStartDate().atZone(ZoneId.systemDefault()).toInstant()));
            cal.setFechaFin(Date.from(event.getEndDate().atZone(ZoneId.systemDefault()).toInstant()));
            cal.setParaUsuario((Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario"));
            cal.setTodoElDia(event.isAllDay());
            //Se añade a la lista de calendarios y al mapa de eventos
            listaCalendarios.add(cal);
            eventMap.put(event.getId(), cal);
            //Se crea el nuevo evento
            calendarioEJB.create(cal);
        }
        else {
            //Se recupera el evento del mapa
            cal = eventMap.get(event.getId());
            //Se establecen los nuevos datos
            cal.setTitulo(event.getTitle());
            cal.setDescripcion(event.getDescription());
            cal.setFechaInicio(Date.from(event.getStartDate().atZone(ZoneId.systemDefault()).toInstant()));
            cal.setFechaFin(Date.from(event.getEndDate().atZone(ZoneId.systemDefault()).toInstant()));
            cal.setParaUsuario((Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario"));
            cal.setTodoElDia(event.isAllDay());
            //Se actualiza el modelo
            eventModel.updateEvent(event);
            //Se edita en BBDD
            calendarioEJB.edit(cal);
        }

        event = new DefaultScheduleEvent<>();
    }

    public void onEventSelect(SelectEvent<ScheduleEvent<?>> selectEvent) {
        event = selectEvent.getObject();
    }

    public void onViewChange(SelectEvent<String> selectEvent) {
        view = selectEvent.getObject();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "View Changed", "View:" + view);
        addMessage(message);
    }

    public void onDateSelect(SelectEvent<LocalDateTime> selectEvent) {
        event = DefaultScheduleEvent.builder()
                .startDate(selectEvent.getObject())
                .endDate(selectEvent.getObject().plusHours(1))
                .build();
    }

    public void onEventMove(ScheduleEntryMoveEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Calendario actualizado", "Se ha movido un evento");
        this.event = (ScheduleEvent<?>) event.getScheduleEvent();
        addEvent();
        addMessage(message);
    }

    public void onEventResize(ScheduleEntryResizeEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Calendario actualizado", "Se ha modificado un evento");
        this.event = (ScheduleEvent<?>) event.getScheduleEvent();
        addEvent();
        addMessage(message);
    }

    public void onEventDelete() {
        if (event != null) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Calendario actualizado", "Se ha eliminado un evento");
            calendarioEJB.remove(eventMap.remove(this.event.getId()));
            eventModel.deleteEvent(event);
            addMessage(message);
        }
    }

    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public boolean isShowWeekends() {
        return showWeekends;
    }

    public void setShowWeekends(boolean showWeekends) {
        this.showWeekends = showWeekends;
    }

    public boolean isSlotEventOverlap() {
        return slotEventOverlap;
    }

    public void setSlotEventOverlap(boolean slotEventOverlap) {
        this.slotEventOverlap = slotEventOverlap;
    }

    public boolean isShowWeekNumbers() {
        return showWeekNumbers;
    }

    public void setShowWeekNumbers(boolean showWeekNumbers) {
        this.showWeekNumbers = showWeekNumbers;
    }

    public boolean isShowHeader() {
        return showHeader;
    }

    public void setShowHeader(boolean showHeader) {
        this.showHeader = showHeader;
    }

    public boolean isDraggable() {
        return draggable;
    }

    public void setDraggable(boolean draggable) {
        this.draggable = draggable;
    }

    public boolean isResizable() {
        return resizable;
    }

    public void setResizable(boolean resizable) {
        this.resizable = resizable;
    }

    public boolean isTooltip() {
        return tooltip;
    }

    public void setTooltip(boolean tooltip) {
        this.tooltip = tooltip;
    }

    public boolean isRtl() {
        return rtl;
    }

    public void setRtl(boolean rtl) {
        this.rtl = rtl;
    }

    public boolean isAllDaySlot() {
        return allDaySlot;
    }

    public void setAllDaySlot(boolean allDaySlot) {
        this.allDaySlot = allDaySlot;
    }

    public double getAspectRatio() {
        return aspectRatio == 0 ? Double.MIN_VALUE : aspectRatio;
    }

    public void setAspectRatio(double aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public String getLeftHeaderTemplate() {
        return leftHeaderTemplate;
    }

    public void setLeftHeaderTemplate(String leftHeaderTemplate) {
        this.leftHeaderTemplate = leftHeaderTemplate;
    }

    public String getCenterHeaderTemplate() {
        return centerHeaderTemplate;
    }

    public void setCenterHeaderTemplate(String centerHeaderTemplate) {
        this.centerHeaderTemplate = centerHeaderTemplate;
    }

    public String getRightHeaderTemplate() {
        return rightHeaderTemplate;
    }

    public void setRightHeaderTemplate(String rightHeaderTemplate) {
        this.rightHeaderTemplate = rightHeaderTemplate;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getNextDayThreshold() {
        return nextDayThreshold;
    }

    public void setNextDayThreshold(String nextDayThreshold) {
        this.nextDayThreshold = nextDayThreshold;
    }

    public String getWeekNumberCalculation() {
        return weekNumberCalculation;
    }

    public void setWeekNumberCalculation(String weekNumberCalculation) {
        this.weekNumberCalculation = weekNumberCalculation;
    }

    public String getWeekNumberCalculator() {
        return weekNumberCalculator;
    }

    public void setWeekNumberCalculator(String weekNumberCalculator) {
        this.weekNumberCalculator = weekNumberCalculator;
    }

    public String getTimeFormat() {
        return timeFormat;
    }

    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }

    public String getSlotDuration() {
        return slotDuration;
    }

    public void setSlotDuration(String slotDuration) {
        this.slotDuration = slotDuration;
    }

    public String getSlotLabelInterval() {
        return slotLabelInterval;
    }

    public void setSlotLabelInterval(String slotLabelInterval) {
        this.slotLabelInterval = slotLabelInterval;
    }

    public String getSlotLabelFormat() {
        return slotLabelFormat;
    }

    public void setSlotLabelFormat(String slotLabelFormat) {
        this.slotLabelFormat = slotLabelFormat;
    }

    public String getDisplayEventEnd() {
        return displayEventEnd;
    }

    public void setDisplayEventEnd(String displayEventEnd) {
        this.displayEventEnd = displayEventEnd;
    }

    public String getScrollTime() {
        return scrollTime;
    }

    public void setScrollTime(String scrollTime) {
        this.scrollTime = scrollTime;
    }

    public String getMinTime() {
        return minTime;
    }

    public void setMinTime(String minTime) {
        this.minTime = minTime;
    }

    public String getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(String maxTime) {
        this.maxTime = maxTime;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getClientTimeZone() {
        return clientTimeZone;
    }

    public void setClientTimeZone(String clientTimeZone) {
        this.clientTimeZone = clientTimeZone;
    }

    public String getColumnHeaderFormat() {
        return columnHeaderFormat;
    }

    public void setColumnHeaderFormat(String columnHeaderFormat) {
        this.columnHeaderFormat = columnHeaderFormat;
    }

    public String getSelectedCalendario() {
        return selectedCalendario;
    }

    public void setSelectedCalendario(String selectedCalendario) {
        this.selectedCalendario = selectedCalendario;
    }

    public String getExtenderCode() {
        return extenderCode;
    }

    public void setExtenderCode(String extenderCode) {
        this.extenderCode = extenderCode;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
}