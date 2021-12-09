const { gql } = require("apollo-server");
const votoTypeDefs = gql`
  type Voto {
    id: String
    codigoUrna:  String!
    idVotante:   String!
    idCandidato: String!
  }

	input nuevoVoto {
    userId: String!
		nombreUsuario:   String!
		nombreCandidato: String!
		codigoUrna:      String!
	}

  extend type Mutation {
    createVoto(newVoto: nuevoVoto!): Voto
  }
`;
module.exports = votoTypeDefs;
