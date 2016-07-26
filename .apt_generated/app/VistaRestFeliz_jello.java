package app;

import com.google.gson.Gson;
import jello.model.EntityDef;
import jello.model.EntityDefFactory;

public class VistaRestFeliz_jello implements EntityDefFactory {

@Override
public EntityDef getEntityDef() {

StringBuffer json = new StringBuffer();

json.append("{");
json.append("	\"name\" : \"app.VistaRestFeliz\",");
json.append("	\"fields\" : [");
json.append("		{");
json.append("			\"name\" : \"apellidoNombre\",");
json.append("			\"type\" : \"java.lang.String\",");
json.append("			\"isPublic\" : true,");
json.append("			\"isStatic\" : false");
json.append("		},");
json.append("		{");
json.append("			\"name\" : \"ruc\",");
json.append("			\"type\" : \"java.lang.String\",");
json.append("			\"isPublic\" : true,");
json.append("			\"isStatic\" : false");
json.append("		},");
json.append("		{");
json.append("			\"name\" : \"Sucursal\",");
json.append("			\"type\" : \"java.lang.Integer\",");
json.append("			\"isPublic\" : true,");
json.append("			\"isStatic\" : false");
json.append("		},");
json.append("		{");
json.append("			\"name\" : \"nroContrato\",");
json.append("			\"type\" : \"java.lang.String\",");
json.append("			\"isPublic\" : true,");
json.append("			\"isStatic\" : false");
json.append("		},");
json.append("		{");
json.append("			\"name\" : \"nroOperacion\",");
json.append("			\"type\" : \"java.lang.String\",");
json.append("			\"isPublic\" : true,");
json.append("			\"isStatic\" : false");
json.append("		},");
json.append("		{");
json.append("			\"name\" : \"nroCuota\",");
json.append("			\"type\" : \"java.lang.String\",");
json.append("			\"isPublic\" : true,");
json.append("			\"isStatic\" : false");
json.append("		},");
json.append("		{");
json.append("			\"name\" : \"fechaVencimiento\",");
json.append("			\"type\" : \"java.util.Date\",");
json.append("			\"isPublic\" : true,");
json.append("			\"isStatic\" : false");
json.append("		},");
json.append("		{");
json.append("			\"name\" : \"totalCuota\",");
json.append("			\"type\" : \"java.lang.Double\",");
json.append("			\"isPublic\" : true,");
json.append("			\"isStatic\" : false");
json.append("		},");
json.append("		{");
json.append("			\"name\" : \"planilla\",");
json.append("			\"type\" : \"java.lang.Double\",");
json.append("			\"isPublic\" : true,");
json.append("			\"isStatic\" : false");
json.append("		},");
json.append("		{");
json.append("			\"name\" : \"diasMora\",");
json.append("			\"type\" : \"java.lang.Integer\",");
json.append("			\"isPublic\" : true,");
json.append("			\"isStatic\" : false");
json.append("		},");
json.append("		{");
json.append("			\"name\" : \"desOperacion\",");
json.append("			\"type\" : \"java.lang.String\",");
json.append("			\"isPublic\" : true,");
json.append("			\"isStatic\" : false");
json.append("		},");
json.append("		{");
json.append("			\"name\" : \"totalMora\",");
json.append("			\"type\" : \"java.lang.Double\",");
json.append("			\"isPublic\" : true,");
json.append("			\"isStatic\" : false");
json.append("		},");
json.append("		{");
json.append("			\"name\" : \"totalDeuda\",");
json.append("			\"type\" : \"java.lang.Double\",");
json.append("			\"isPublic\" : true,");
json.append("			\"isStatic\" : false");
json.append("		}");
json.append("	],");
json.append("	\"actions\" : [");
json.append("		{");
json.append("			\"name\" : \"borrarPlanilla\",");
json.append("			\"isPublic\" : true,");
json.append("			\"isStatic\" : true,");
json.append("			\"isAsync\" : null,");
json.append("			\"accessible\" : [],");
json.append("			\"params\" : [");
json.append("			],");
json.append("			\"returnType\" : \"java.util.List<app.VistaRestFeliz>\"");
json.append("		}");
json.append("	]");
json.append("}");

return (new Gson()).fromJson(json.toString(), EntityDef.class);
}
}
