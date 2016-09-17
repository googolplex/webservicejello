package parqueserenidad;

import jello.annotation.Expose;
import jello.annotation.KeyElement;
import jello.annotation.ReadOnly;
import jello.annotation.Required;
import jello.model.JelloEntity;
import jello.model.JelloModel;
import jello.rest.IllegalRequestResource;
import jello.rest.Options;
import jello.security.Accessible;

import java.util.List;

import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;


@PersistenceCapable
@Accessible
public class Consulta extends JelloEntity {
	private static final long serialVersionUID = -374009095658106948L;
	
	
	@Expose
	// @Expose({Role.ADMIN,Role.SYSTEM})
	@KeyElement 
	@Required
	public String ruc;
	
	@NotPersistent
	@Expose 
	public Integer cantDetalles = 0;
	
	/*public Integer cantDetalles(){
		String filtro = "ruc == '" + this.ruc + "'";
		@SuppressWarnings("unchecked")
		List<VistaRestFeliz> aux = (List<VistaRestFeliz>) 
				JelloModel.select(VistaRestFeliz.class, filtro);
		return aux.size();
	}*/

	@Expose @NotPersistent
	public Integer codRetorno = 0;
	/* public Integer codRetorno(){
		String filtro = "ruc == '" + this.ruc + "'";
		Integer instancias = JelloModel.count(VistaRestFeliz.class, filtro);
		switch (instancias){
			case 0:
				return 1;
			default:
				return 0;
		}
	}*/

 
	@NotPersistent
	@ReadOnly // avoid firing calculated field methos on INSERT or UPDATE REST
	@Expose	
	 public List<VistaRestFeliz> consultaDetalles;
	 public List<VistaRestFeliz> consultaDetalles(){
		 Double maxplanilla = Planillas.maxPlanilla();
		// String filtro = "ruc == '" + this.ruc + "'";
		/**
		 * Formato anterior, sin $top
		String filtro = "planilla eq " + maxplanilla + " & ruc eq '" + this.ruc +"'";
		String orden = "fechaVencimiento ASC";
		**/
		Options opciones = Options.getDefaults();
		opciones.filter = "planilla eq " + maxplanilla + " & ruc eq '" + this.ruc +"'";
		opciones.orderBy = "fechaVencimiento ASC";
		opciones.top = 8;
		@SuppressWarnings("unchecked")
		List<VistaRestFeliz> aux = (List<VistaRestFeliz>) JelloModel.select(VistaRestFeliz.class, opciones);
		this.cantDetalles = aux.size();
		// no tiene cuotas pendientes de cobro
		if (this.cantDetalles.equals(0)) {
			this.codRetorno = 1;
		}
		return aux;
	}
	
	@Expose @NotPersistent
	public String desRetorno;
	public String desRetorno(){
		switch (this.codRetorno){
			case 0:
				return "Aprobado";
			case 1:
				return "Sin cuotas pendientes";
			default:
				return "No encontrado";
		}
	}
	
 
	// @NotPersistent
	@Expose	
	public String nombreApellido;
	
	// public String nombreApellido() throws IllegalRequestResource{
	//	VistaRestFeliz aux = new VistaRestFeliz();
	//	String filtro = "ruc == '" + this.ruc + "'";
	//	aux = (VistaRestFeliz) JelloModel.selectOne(VistaRestFeliz.class, filtro);
	//	return aux.nombreApellido;
	// }
	

	@Accessible
	@SuppressWarnings("unchecked")
	public static String borrarConsulta() throws IllegalRequestResource {
		List<Consulta> consultas = (List<Consulta>) JelloModel.select(Consulta.class);
		String ruc = "";
		Consulta au;
		for (int i = 0; i < consultas.size() ; i++){
			try {
			au = consultas.get(i);
			ruc = au.getID();
			JelloModel.delete(Consulta.class, ruc);
			} catch (Exception e) {
				System.out.print("<br>el registro ya no estaba en el storage->" + ruc );
			}
			
		}	
		
		return "<br>Fin:Consultas borrada exitosamente";
	}
	
	@Accessible
	@SuppressWarnings("finally")
	public static String borrarConsulta2() throws IllegalRequestResource{
		String filtro = "ruc > 0";
		try{
			List<String> instancias = JelloModel.selectIds(Consulta.class, filtro);
			JelloModel.delete(VistaRestFeliz.class, instancias);
			System.out.print("<h3>Borrado completo");
		}
		catch(Exception motivoDeLaQueja){
			System.out.print("<h3>Error en el borrado");
		}
		finally{
			return "<h3>Ejecucion terminada";
		}
	}
}