package app;

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
		
	@Expose @Required
	public String apellidoNombre;
		
	@Expose @Required
	public String ruc;	
	
	@Expose @Required	
	public Integer Sucursal ;


	@Expose @KeyElement @Required
	public String nroContrato;
	
	@Expose @KeyElement @Required
	public String nroOperacion;	
	
	@Expose  @Required
	public String nroCuota;
	
	@Expose @Required
	public Date fechaVencimiento;	
	
	@Expose @Required
	public Double totalCuota = 0.0D;
	
	@Expose @Required
	public Long planilla;
	
	@NotPersistent @Expose
	public Integer diasMora = 0;
	public Integer diasMora() {
		DateTime ahoramismo = new DateTime() ;
		DateTime fechora = new DateTime(fechaVencimiento);
		// http://stackoverflow.com/questions/1555262/calculating-the-difference-between-two-java-date-instances
		Days d = Days.daysBetween(fechora, ahoramismo);
		diasMora = d.getDays();
		return  diasMora ;
	}

	@NotPersistent @Expose
	public String desOperacion ;
	public String desOperacion() {
			desOperacion = nroContrato.substring(0,3);
		return desOperacion ;
	}
	
	@NotPersistent @Expose
	// parque calcula de otra forma
	public Double totalMora = 0.0D;
	
	@NotPersistent @Expose
	public Double totalDeuda = 0.0D;
	public Double totalDeuda() {
		// no se cobra intereses por mora de esta forma en parque
		this.totalDeuda = this.totalCuota +  this.totalMora ;
		return totalDeuda ;
	}
	
	@Override
	protected VistaRestFeliz beforeSave() throws IllegalRequestResource {
			this.ruc = String.format("%15s", ruc.trim()).replace(' ', '0') ;
			this.nroCuota = String.format("%7s", nroCuota.trim()).replace(' ', '0');	
			this.apellidoNombre = this.apellidoNombre.trim().toUpperCase();
			this.nroContrato = this.nroContrato.trim().toUpperCase();
	//   if(something-fishy)
	//      throw new IllegalRequestResource( "something fishy while trying to save " +  this.getID() );
		return this;
	}
	@Accessible 
	@SuppressWarnings("unchecked")
	public static List<VistaRestFeliz> borrarPlanilla() throws IllegalRequestResource {
		JelloModel.deleteAll(VistaRestFeliz.class);
		// .deleteByQuery(VistaRestFeliz.class, "ruc==ruc"); 
	/*	for(int i=0; i<10; i++) {
			VistaRestFeliz p = new VistaRestFeliz();
			p.apellidoNombre = "BORRADO " + i;
			p.desOperacion = "BORRADO " + i;
			p.fechaVencimiento = new Date();
			p.nroContrato = "CMS";
			p.nroCuota = "01-01";
			p.nroOperacion = "-121212";
			p.planilla = 0L;
			p.ruc = "99" ;
			p.Sucursal = 1;
			p.totalCuota = 1D;
			p.create();
		}*/
		
		return (List<VistaRestFeliz>) JelloModel.select(VistaRestFeliz.class);
	}	
}
