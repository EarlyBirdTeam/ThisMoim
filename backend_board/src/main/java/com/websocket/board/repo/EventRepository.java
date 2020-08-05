public interface EventRepository extends JpaRepository<Event, Long> {

}

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventService {

    private final EventRepository eventRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public EventResponseDto save(EventSaveRequestDto dto, String email) {

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        Event event = dto.toEntity(member);

        eventRepository.save(event);

        return new EventResponseDto(event);
    }

}