const { gql } = require('apollo-server');

const candidatoTypeDefs = gql `

	type Candidato {
		id: String!
		codigoUrna: String!
		nombreCompleto: String!
		descripcion:String!
		votos: List<Voto>
	}	
	input nuevoCandidato{
		nombreUsuario:String!
		nombreCandidato:String!
		codigoUrna:String!
		descripcion:String!
		fecha:Date!
	}
	
	extend type Mutation {
		createCandidato(newCandidato: nuevoCandidato!): Candidato
	}
	`;
	module.exports = candidatoTypeDefs;
	
