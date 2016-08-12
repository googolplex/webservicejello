package parqueserenidad;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;

import org.joda.time.DateTime;
import org.joda.time.Days;

import jello.annotation.Expose;
import jello.annotation.KeyElement;
import jello.annotation.Required;
import jello.model.JelloEntity;
import jello.model.JelloModel;
import jello.rest.IllegalRequestResource;
import jello.security.*;
import jello.ux.Validation;
import parqueserenidad.VistaRestFeliz;


 
/* @Accessible(
		   retrieve = Role.ALL,
		   create = "AppAdminRole",
		   update = { Role.ADMIN, Role.OWNER, Security.ByAction + "app.partners.SendInvite" },
		   query = Role.USER,
		   delete = { Role.ADMIN, Role.SYSTEM}
		)
		*/
@Accessible 
@PersistenceCapable
public class VistaRestFeliz extends JelloEntity  {
	private static final long serialVersionUID = -374005810691063548L;
		
				
	@Expose
	//@Expose({Role.ADMIN,Role.SYSTEM})
	@Required	
	public String ruc;	
	
	@Expose 
	@Required	
	public String Sucursal ;


	@Expose 
	@KeyElement 
	@Required
	public String nroContrato;
	
	@Expose 
	@KeyElement 
	@Required
	public String nroOperacion;	
	
	@Expose  @Required
	public String nroCuota;
	
	@Expose 
	//@Expose({Role.ADMIN,Role.SYSTEM})
	@Required
	public Date fechaVencimiento;	
	
	@Expose
	//@Expose({Role.ADMIN,Role.SYSTEM}) 
	@Required
	@Validation(min=0)	
	public Double totalCuota = 0.0D;
	
	@Expose 
	@NotPersistent
	public String totalCuotaS ;
	public String totalCuotaS() {
		return String.format("%013.0f", this.totalCuota) + "00";
	}
	
	@Expose
	//@Expose({Role.ADMIN,Role.SYSTEM})
	@Required
	@Validation(min=0)
	public Double planilla = 0.0D;
	
	@NotPersistent 
	@Expose 
	@Accessible
	public Integer diasMora;
	public Integer diasMora() {
		DateTime ahoramismo = new DateTime() ;
		DateTime fechora = new DateTime(fechaVencimiento);
		// http://stackoverflow.com/questions/1555262/calculating-the-difference-between-two-java-date-instances
		Days d = Days.daysBetween(fechora, ahoramismo);
		Integer dias = d.getDays();
		if (dias < 0){
			return 0;
		}
		else{
			return  dias;
		}
	}	

	@Expose @Required
	public String desOperacion ;
	
	@NotPersistent 
	@Expose
	//@Expose({Role.ADMIN,Role.SYSTEM})
	public Double totalMora = 0.0D;
	public Double totalMora(){
		String centro = this.nroOperacion.substring(1, 4);
		// System.out.println(centro);
		switch(centro){
			case "PSM":
				return 0d;
			case "CMS":
				if (this.diasMora > 30 && this.diasMora < 365){
					return (double) Math.round(this.totalCuota * 0.1d);
				
				}
				else{
					return 0d;
				}
			default:
				return (double) Math.round(this.diasMora * 0.001d * this.totalCuota);
		}
	}	
	
	@Expose 
	@NotPersistent
	public String totalMoraS ;
	public String totalMoraS() {
		// porque no se ejecuta cuando el rol no lo permite
		this.totalMora = this.totalMora();
		return String.format("%013.0f", this.totalMora) + "00";
	}
	
	@NotPersistent @Expose @Accessible
	public String fechaVenceString;
	public String fechaVenceString(){
		DateFormat formato = new SimpleDateFormat("ddMMyyyy");
		return formato.format(this.fechaVencimiento);
	}	
	
	@NotPersistent 
	@Expose
	// @Expose({Role.ADMIN,Role.SYSTEM})
	public Double totalDeuda = 0.0D;
	public Double totalDeuda() {
		// no se cobra intereses por mora de esta forma en parque
		this.totalDeuda = this.totalCuota +  this.totalMora ;
		return totalDeuda ;
	}

	@Expose @NotPersistent
	public String totalDeudaS ;
	public String totalDeudaS() {
		// porque no se ejecuta cuando el rol no lo permite
		this.totalDeuda = this.totalDeuda() ;
		return String.format("%013.0f", this.totalDeuda) + "00";
	}	
	
	@Override
	protected VistaRestFeliz beforeSave() throws IllegalRequestResource {
			this.ruc = String.format("%15s", ruc.trim()).replace(' ', '0') ;
			this.nroCuota = String.format("%7s", nroCuota.trim()).replace(' ', '0');	
			this.nroContrato = this.nroContrato.trim().toUpperCase();
	//   if(something-fishy)
	//      throw new IllegalRequestResource( "something fishy while trying to save " +  this.getID() );
		return this;
	}
	
	@Accessible
	@SuppressWarnings("unchecked")
	public static String borrarVistaRestFeliz() throws IllegalRequestResource {
		List<VistaRestFeliz> registros = (List<VistaRestFeliz>) JelloModel.select(VistaRestFeliz.class);
		String id = "";
		VistaRestFeliz aux;
		for (int i = 0; i < registros.size() ; i++){
			try {
			aux = registros.get(i);
			id = aux.getID();
			JelloModel.delete(VistaRestFeliz.class, id);
			} catch  (Exception e)
			{
				System.out.print("<br>el registro ya no estaba en el storage->" + id);
			}
		}
		return "<br>Fin: Planilla borrada exitosamente";
	}	
	
	
}
