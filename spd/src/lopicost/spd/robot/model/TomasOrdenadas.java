package lopicost.spd.robot.model;


import java.util.ArrayList;
import java.util.List;

public class TomasOrdenadas {


	    private List<Integer> posiciones;
	    private List<String> nombresTomas;
	    private List<String> idTomas;

	    public TomasOrdenadas(List<Integer> posiciones, List<String> nombresTomas, List<String> idTomas) {
	        this.posiciones = posiciones;
	        this.nombresTomas = nombresTomas;
	        this.idTomas = idTomas;
	    }

	    public List<Integer> getPosiciones() {
	        return posiciones;
	    }

	    public List<String> getNombresTomas() {
	        return nombresTomas;
	    }

	    public List<String> getIdTomas() {
	        return idTomas;
	    }
	}
