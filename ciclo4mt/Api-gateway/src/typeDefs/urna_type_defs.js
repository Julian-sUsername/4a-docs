const { gql } = require("apollo-server");
const urnaTypeDefs = gql`
  type Urna {
    codigo: String
    nombre: String
    descripcion: String
    fecha: String
    candidatos: [Candidato!]!
    idAdmin: String
    resultados: String
    ganador: String
  }

  input nuevaUrna {
    userId: Int!
    nombre: String!
    descripcion: String!
  }

  input abrirUrna {
    userId: String!
    codigoUrna: String!
  }

  input cerrarUrna {
    userId: String!
    codigoUrna: String!
  }

  input eliminarUrna {
    userId: String!
    codigoUrna: String!
  }

  input urnaByCodigoInput {
    userId: String!
    codigo: String!
  }

  extend type Mutation {
    createUrna(newUrna: nuevaUrna!): Urna
    openUrna(urnaAbierta: abrirUrna!): Urna
    closeUrna(urnaCerrada: cerrarUrna!): Urna
    deleteUrna(urnaEliminada: eliminarUrna!): Urna
  }

  extend type Query {
    urnaByCodigo(codigoInput: urnaByCodigoInput!): Urna
    resultadosUrna(codigoInput: urnaByCodigoInput!): Urna
  }

`;
module.exports = urnaTypeDefs;
