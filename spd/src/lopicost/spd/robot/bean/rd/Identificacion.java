package lopicost.spd.robot.bean.rd;



public class Identificacion {
    private String forma;
    private String color1;
    private String color2;
    private String ranura;
    private String largo;
    private String ancho;
    private String inscripcionA;
    private String inscripcionB;
    private String dibujo;
    private String resumen;
    
    private String getSeparador(String resumen) {
    	String separador=" | ";
    	if(resumen==null || resumen.trim().isEmpty())
    		separador=" ";
    	return separador;
    }
    
	public String getResumen() {
		String resumen="";
		
		resumen+=forma!=null&&!forma.isEmpty()?" " +forma:"";
		resumen+=ranura!=null&&!ranura.isEmpty()?getSeparador(resumen) + "Ranura: " +ranura:"";
		if( color1!=null&&!color1.trim().isEmpty() &&  color2!=null&&!color2.trim().isEmpty()){
			resumen+= getSeparador(resumen) + "Colores: " + color1 + " - " + color2;	
		}
		else 
		{
			if( color1!=null&&!color1.trim().isEmpty())
			{
				resumen+= getSeparador(resumen) + "Color: " + color1;
			}
			else if( color2!=null&&!color2.trim().isEmpty())
			{
				resumen+= getSeparador(resumen) + "Color: " + color2;
			}
				
		}
		
		resumen+=largo!=null&&!largo.isEmpty()?getSeparador(resumen) + "Largo: " + largo + "mm.":"";
		resumen+=ancho!=null&&!ancho.isEmpty()?getSeparador(resumen) + "Ancho: " + ancho + "mm.":"";
		if( inscripcionA!=null&&!inscripcionA.trim().isEmpty() &&  inscripcionB!=null&&!inscripcionB.trim().isEmpty()){
			resumen+= getSeparador(resumen) + "Inscripciones: " + inscripcionA + " - " + inscripcionB;	
		}
		else 
		{
			if( inscripcionA!=null&&!inscripcionA.trim().isEmpty())
			{
				resumen+= getSeparador(resumen) + "Color: " + inscripcionA;
			}
			else if( inscripcionB!=null&&!inscripcionB.trim().isEmpty())
			{
				resumen+= getSeparador(resumen) + "Color: " + inscripcionB;
			}
				
		}
		
			
		if( dibujo!=null&&dibujo.equalsIgnoreCase("SI") )
		{
			resumen+= getSeparador(resumen) + "Dibujo SI ";	
		}
		return resumen;
	}
	public void setResumen(String resumen) {
		this.resumen = resumen;
	}
	
	
	public String getForma() {
		return forma;
	}
	public void setForma(String forma) {
		this.forma = forma;
	}
	public String getColor1() {
		return color1;
	}
	public void setColor1(String color1) {
		this.color1 = color1;
	}
	public String getColor2() {
		return color2;
	}
	public void setColor2(String color2) {
		this.color2 = color2;
	}
	public String getRanura() {
		return ranura;
	}
	public void setRanura(String ranura) {
		this.ranura = ranura;
	}
	public String getLargo() {
		return largo;
	}
	public void setLargo(String largo) {
		this.largo = largo;
	}
	public String getAncho() {
		return ancho;
	}
	public void setAncho(String ancho) {
		this.ancho = ancho;
	}
	public String getInscripcionA() {
		return inscripcionA;
	}
	public void setInscripcionA(String inscripcionA) {
		this.inscripcionA = inscripcionA;
	}
	public String getInscripcionB() {
		return inscripcionB;
	}
	public void setInscripcionB(String inscripcionB) {
		this.inscripcionB = inscripcionB;
	}
	public String getDibujo() {
		return dibujo;
	}
	public void setDibujo(String dibujo) {
		this.dibujo = dibujo;
	}
    
    

}
