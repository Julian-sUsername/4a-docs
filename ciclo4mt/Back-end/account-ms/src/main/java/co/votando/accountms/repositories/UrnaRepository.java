package co.votando.accountms.repositories;

import co.votando.accountms.models.Urna;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UrnaRepository extends MongoRepository <Urna, String>{

    List<Urna> findByCodigo (String codigo);

}
