const { gql } = require("apollo-server");
const candidatoTypeDefs = gql`
  type Candidato {
    id:             String
    codigoUrna:     String!
    nombreCompleto: String!
    descripcion:    String!
    votos:          [Voto]
  }

  input CandidatoInput {
    codigoUrna:     String!
    nombreCompleto: String!
    descripcion:    String!
  }

  extend type Mutation {
    createCandidato(candidato: CandidatoInput!): Candidato
  }
`;
module.exports = candidatoTypeDefs;
