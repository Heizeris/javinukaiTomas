package lt.javinukai.javinukai.repository;


import lt.javinukai.javinukai.entity.ParticipationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ParticipationRequestRepository extends JpaRepository<ParticipationRequest, UUID> {
}
