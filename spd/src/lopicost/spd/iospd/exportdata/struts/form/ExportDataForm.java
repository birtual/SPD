
package lopicost.spd.iospd.exportdata.struts.form;



import java.util.List;

import org.apache.struts.action.ActionForm;


public class ExportDataForm extends ActionForm 
{
    private List datos;
    private List errors;

    /**
     * @return Returns the datos.
     */
    public List getDatos() {
        return datos;
    }

    /**
     * @param datos The datos to set.
     */
    public void setDatos(List datos) {
        this.datos = datos;
    }

	/**
	 * @return Returns the errors.
	 */
	public List getErrors() {
		return errors;
	}

	/**
	 * @param errors The errors to set.
	 */
	public void setErrors(List errors) {
		this.errors = errors;
	}
    
}