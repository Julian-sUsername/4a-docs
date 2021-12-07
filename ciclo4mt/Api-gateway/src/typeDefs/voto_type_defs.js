const { gql } = require("apollo-server");
const votoTypeDefs = gql`
  type Voto {
    id: String
    codigoUrna:  String!
    idVotante:   String!
    idCandidato: String!
    fecha:       Date
  }

	input nuevoVoto {
		nombreUsuario:   String!
		nombreCandidato: String!
		codigoUrna:      String!
	}

  extend type Mutation {
    createVoto(newVoto: nuevoVoto!): Voto
  }
`;
module.exports = votoTypeDefs;
