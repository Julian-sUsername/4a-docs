const urnasResolver = {
  Query: {
    urnaByCodigo: (_, { codigoInput }, { dataSources, userIdToken }) => {
      if (codigoInput.userId == userIdToken)
        return dataSources.accountAPI.getUrna(codigoInput.codigo);
      else return null;
    },
    resultadosUrna: (_, { codigoInput }, { dataSources, userIdToken }) => {
      if (codigoInput.userId == userIdToken)
        return dataSources.accountAPI.resultadosUrna(codigoInput.codigo);
      else return null;
    },
  },
  Mutation: {
    createUrna: async (_, { newUrna }, { dataSources, userIdToken }) => {
      usernameToken = (await dataSources.authAPI.getUser(userIdToken)).username      
      if (newUrna.userId == userIdToken) {
        const nuevaUrna = {
          nombre: newUrna.nombre,
          descripcion: newUrna.descripcion,
          idAdmin: usernameToken,
        };
        return await dataSources.accountAPI.createUrna(nuevaUrna);
      } else return null;
    },
    openUrna: async (_, { urnaAbierta }, { dataSources, userIdToken  }) => {
      usernameToken = (await dataSources.authAPI.getUser(userIdToken)).username
      admin = (await dataSources.accountAPI.getUrna(urnaAbierta.codigoUrna)).idAdmin
      if (urnaAbierta.userId == userIdToken && usernameToken == admin) {  
        return await dataSources.accountAPI.openUrna(urnaAbierta.codigoUrna);
      } else return null;
    },
    closeUrna: async (_, { urnaCerrada }, { dataSources, userIdToken  }) => {
      usernameToken = (await dataSources.authAPI.getUser(userIdToken)).username
      admin = (await dataSources.accountAPI.getUrna(urnaCerrada.codigoUrna)).idAdmin
      if (urnaCerrada.userId == userIdToken && usernameToken == admin) {  
        return await dataSources.accountAPI.closeUrna(urnaCerrada.codigoUrna);
      } else return null;
    },
    deleteUrna: async (_, { urnaEliminada }, { dataSources, userIdToken  }) => {
      usernameToken = (await dataSources.authAPI.getUser(userIdToken)).username
      admin = (await dataSources.accountAPI.getUrna(urnaEliminada.codigoUrna)).idAdmin
      if (urnaEliminada.userId == userIdToken && usernameToken == admin) {  
        return await dataSources.accountAPI.eliminarUrna(urnaEliminada.codigoUrna);
      } else return null;
    },
  },
};
module.exports = urnasResolver;
