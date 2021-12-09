const { gql } = require("apollo-server");
const candidatoTypeDefs = gql`
  type Candidato {
    codigoUrna:     String
    nombreCompleto: String
    descripcion:    String
  }

  input CandidatoInput {
    userId: String!
    codigoUrna:     String!
    nombreCompleto: String!
    descripcion:    String!
  }

  input eliminarCandidato {
    userId: String!
    codigo: String!
  }

  extend type Mutation {
    createCandidato(candidato: CandidatoInput!): Candidato
    removeCandidato(deleteCandidato: eliminarCandidato!): Candidato
  }
`;
module.exports = candidatoTypeDefs;
