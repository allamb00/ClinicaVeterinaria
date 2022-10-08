/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import javax.faces.convert.Converter;

/**
 *
 * @author andre
 */
@FacesConverter("descuentoConverter")
public class DescuentoConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {
            switch(Integer.parseInt(submittedValue)){
                case 0:
                    return "Sin descuento";
                case 1: 
                    return "Estudiantes";
                case 2:
                    return "Protectora";
                default:
                    return null;
            }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object modelValue) {
            switch((int)modelValue){
                case 0:
                    return "Sin descuento";
                case 1: 
                    return "Estudiantes";
                case 2:
                    return "Protectora";
                default:
                    return null;
            }
    }
}
