const candidatosResolver = {
  Mutation: {
    createCandidato: async (_, { candidato }, { dataSources, userIdToken }) => {
      usernameToken = (await dataSources.authAPI.getUser(userIdToken)).username
      admin = (await dataSources.accountAPI.getUrna(candidato.codigoUrna)).idAdmin
      if (candidato.userId == userIdToken && usernameToken == admin) {
        const nuevoCandidato = {
          codigoUrna: candidato.codigoUrna,
          nombreCompleto: candidato.nombreCompleto,
          descripcion: candidato.descripcion,
        };
        return await dataSources.accountAPI.createCandidato(nuevoCandidato);
      } else return null;
    },
    removeCandidato: async (_, { deleteCandidato }, { dataSources, userIdToken }) => {
      usernameToken = (await dataSources.authAPI.getUser(userIdToken)).username
      admin = (await dataSources.accountAPI.getUrna(deleteCandidato.codigo.substring(0, 15))).idAdmin
      if (deleteCandidato.userId == userIdToken && usernameToken == admin) {
        return await dataSources.accountAPI.removeCandidato(deleteCandidato.codigo);
      } else return null;
    }
  },
};
module.exports = candidatosResolver;
