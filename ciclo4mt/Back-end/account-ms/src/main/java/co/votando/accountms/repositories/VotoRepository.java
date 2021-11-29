package co.votando.accountms.repositories;

import co.votando.accountms.models.Voto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VotoRepository extends MongoRepository <Voto, String>{ }
