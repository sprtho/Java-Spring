package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

//@Repository -> SpringConfig class로 이관
public class MemoryMemberRepository implements MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id)
    {
        return Optional.ofNullable(store.get(id)); // NULL이 반환될 수 있는 부분을 Optional 함수로 감싸줌
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream() // values() 메소드는 열거체의 모든 상수를 저장한 배열을 생성하여 반환한다.
                .filter(member -> member.getName().equals(name))
                .findAny(); // stream에서 가장 먼저 탐색되는 요소를 리턴
    }

    @Override
    public List<Member> findAll() {

        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
