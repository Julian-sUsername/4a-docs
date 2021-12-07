const { gql } = require("apollo-server");
const urnaTypeDefs = gql`
  type Urna {
    codigo:       String
    nombre:       String!
    descripcion:  String!
    fecha:        String
    esDisponible: boolean
		candidatos:   [Candidato]
  }

  input nuevaUrna {
		nombre:      String!
		descripcion: String!
	}

  input abrirUrna {
		codigoUrna: String!	
	}

  input cerrarUrna {
		codigoUrna: String!
	}

  input eliminarUrna {
		codigoUrna: String!
	}

  extend type Mutation {
    createUrna(newUrna:    nuevaUrna!):       Urna
    openUrna(urnaAbierta:  abrirUrna!):       Urna
    closeUrna(urnaCerrada: cerrarUrna!):      Urna
    deleteUrna(urnaEliminada: eliminarUrna!): Urna
  }

  extend type Query {
    urnaByCodigo(codigo: String!): Urna
  }
`;
module.exports = urnaTypeDefs;
