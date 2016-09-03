package parqueserenidad;

import jello.model.JelloEntity;
import jello.model.JelloModel;
import jello.rest.IllegalRequestResource;
import jello.rest.Options;
import jello.annotation.Expose;
import jello.security.Accessible;
import jello.annotation.KeyElement;
import jello.security.Role;

import java.util.List;


import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable
@Accessible( Role.ALL )
public class Planillas extends JelloEntity {

	private static final long serialVersionUID = -6295748785745439833L;

	@Expose
	@KeyElement
	public Double numero;

	@Accessible
	@SuppressWarnings("unchecked")
	// esto es para consultar el numero de planilla 12.8.2016
	public static Double maxPlanilla()  {
	Options opciones = Options.getDefaults();
	opciones.orderBy = "numero DESC";
	opciones.top = 1;
	List<Planillas> fetch = (List<Planillas>) JelloModel.select(Planillas.class, opciones);
	Planillas max = fetch.get(0);

	return max.numero;
	        }
	

	@Accessible
	@SuppressWarnings("unchecked")
	public static void  traerUltimaPlanilla() throws IllegalRequestResource  {
		Options opciones = Options.getDefaults();
		opciones.orderBy = "planilla DESC";
		opciones.top = 1;
		List<VistaRestFeliz> fetch = (List<VistaRestFeliz>) JelloModel.select(VistaRestFeliz.class, opciones);
		VistaRestFeliz p = fetch.get(0);
		Planillas max = new Planillas();
		max.numero = p.planilla;
		try{
			max.create();
		}
		catch(Exception e){
			
		}
	}

	
}
