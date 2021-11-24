package co.votando.accountms.repositories;

import co.votando.accountms.models.Votante;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VotanteRepository extends MongoRepository <Votante, String>{ }
