package co.votando.accountms.repositories;

import co.votando.accountms.models.Candidato;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CandidatoRepository extends MongoRepository <Candidato, String>{ }
