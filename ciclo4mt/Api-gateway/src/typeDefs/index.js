const candidatoTypeDefs = require ('./candidato_type_defs');
const urnaTypeDefs = require ('./urna_type_defs');
const votanteTypeDefs = require ('./votante_type_defs');
const votoTypeDefs = require ('./voto_type_defs');

const schemasArrays = [votanteTypeDefs, urnaTypeDefs, candidatoTypeDefs, votoTypeDefs];

module.exports = schemasArrays;