const votosResolver = {
    Mutation: {
        createVoto: async (_, { newVoto }, { dataSources, userIdToken }) => {
        usernameToken = (await dataSources.authAPI.getUser(userIdToken)).username
        user = newVoto.nombreUsuario
        if (newVoto.userId == userIdToken && usernameToken == user) {
          const nuevoVoto = {
            codigoUrna: newVoto.codigoUrna,
            idVotante: newVoto.nombreUsuario,
            idCandidato: newVoto.nombreCandidato,
          };
          return await dataSources.accountAPI.createVoto(nuevoVoto);
        } else return null;
      }
    },
  };
  module.exports = votosResolver;