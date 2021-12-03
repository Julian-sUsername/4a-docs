const { gql } = require('apollo-server');

const votanteTypeDefs = gql `

	type Votante {
		id: String!
		nombreUsuario: String!
		nombreCompleto: String!
		correo:String!
		urnas: List<Urna>
	}	
	input nuevoVotante{
		nombreUsuario:String!
		nombreCandidato:String!
		codigoUrna:String!
		fecha:Date!
	}
	
	extend type Mutation {
	createVotante(voter: nuevoVotante!): Votante
	}
	`;
	module.exports = votanteTypeDefs;
