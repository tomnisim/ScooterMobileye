package gotcha.server.Domain.UserModule;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IAvailableRaspberryPiSerialsRepository extends JpaRepository<RaspberryPiSerial,String>
{
}

