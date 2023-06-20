package br.com.vulcan.jvulcan.api.entity.servidores.repository;

import br.com.vulcan.jvulcan.api.entity.servidores.model.ServidorAutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServidorAutorRepository extends JpaRepository<ServidorAutor, Long> {

}
