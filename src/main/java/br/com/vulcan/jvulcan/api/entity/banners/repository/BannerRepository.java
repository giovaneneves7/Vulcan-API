package br.com.vulcan.jvulcan.api.entity.banners.repository;

import br.com.vulcan.jvulcan.api.entity.banners.model.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Long>
{

    Optional<Banner> findBannerByNome(String nome);

}
