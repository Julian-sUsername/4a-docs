const { RESTDataSource } = require('apollo-datasource-rest');
const serverConfig = require('../server');

    class AccountAPI extends RESTDataSource {
        constructor() {
        super();
        this.baseURL = serverConfig.account_api_url;
        }
    
        async createCandidato(newCandidato) {
            candidato = new Object(JSON.parse(JSON.stringify(newCandidato)));
            return await this.post('/candidatos', newCandidato);
        }
        
        async createVoto(newVoto) {
            voto = new Object(JSON.parse(JSON.stringify(newVoto)));
            return await this.post('/votos', newVoto);
        }
        
        async createVotante(voter) {
            votante = new Object(JSON.parse(JSON.stringify(voter)));
            return await this.post('/votantes', voter);
        }

        async urnaByCodigo(codigo) {
            return await this.get(`/urnas/${codigo}`);
        }
        
        async createUrna(newUrna) {
            urna = new Object(JSON.parse(JSON.stringify(newUrna)));
            return await this.post('/urnas', newUrna);
        }
        
        async openUrna(urnaAbierta) {
            urna = new Object(JSON.parse(JSON.stringify(urnaAbierta)));
            return await this.post('/urnas', urnaAbierta);
        }

        async closeUrna(urnaCerrada) {
            urna = new Object(JSON.parse(JSON.stringify(urnaCerrada)));
            return await this.post('/urnas', urnaCerrada);
        }
        
    }
module.exports = AccountAPI;