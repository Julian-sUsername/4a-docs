const votante_resolver = require('./votante_resolver');
const urna_resolver = require('./urna_resolver');
const candidato_resolver = require('./candidato_resolver');
const voto_resolver = require('./voto_resolver');

const lodash = require('lodash');

const resolvers = lodash.merge(votante_resolver, urna_resolver, candidato_resolver, voto_resolver);

module.exports = resolvers;