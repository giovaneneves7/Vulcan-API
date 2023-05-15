package br.com.vulcan.jvulcan.api.banners.repository;

import br.com.vulcan.jvulcan.api.banners.model.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Long>
{

}
