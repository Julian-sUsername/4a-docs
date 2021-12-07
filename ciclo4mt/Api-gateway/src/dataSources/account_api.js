const { RESTDataSource } = require("apollo-datasource-rest");
const serverConfig = require("../server");
class AccountAPI extends RESTDataSource {
  constructor() {
    super();
    this.baseURL = serverConfig.account_api_url;
  }

  async createVotante(votante) {
    votante = new Object(JSON.parse(JSON.stringify(votante)));
    return await this.post(`/votantes`, votante);
  }

  async createUrna(urna) {
    urna = new Object(JSON.parse(JSON.stringify(urna)));
    return await this.post("/urnas/", urna);
  }

  async openUrna(codigoUrna) {
    return await this.post(`/urnas/abrir/${codigoUrna}`);
  }

  async closeUrna(codigoUrna) {
    return await this.post(`/urnas/cerrar/${codigoUrna}`);
  }

  async resultadosUrna(codigoUrna) {
    return await this.get(`/urnas/resultados/${codigoUrna}`);
  }

  async eliminarUrna(codigoUrna) {
    return await this.post(`/urnas/eliminar/${codigoUrna}`);
  }

  async createCandidato(candidato) {
    candidato = new Object(JSON.parse(JSON.stringify(candidato)));
    return await this.post("/candidatos/", candidato);
  }

  async removeCandidato(codigoCandidato) {
    return await this.post(`/candidatos/eliminar/${codigoCandidato}`);
  }

  async createVoto(voto) {
    voto = new Object(JSON.parse(JSON.stringify(voto)));
    return await this.post("/votos/", voto);
  }
}
module.exports = AccountAPI;
