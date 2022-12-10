package soccerfriend.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import soccerfriend.dto.Member;
import soccerfriend.exception.exception.BadRequestException;
import soccerfriend.exception.exception.DuplicatedException;
import soccerfriend.mapper.MemberMapper;
import soccerfriend.utility.CodeGenerator;
import soccerfriend.utility.InputForm.UpdatePasswordRequest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberMapper memberMapper;

    @Mock
    private EncryptService encryptService;

    @Mock
    private EmailService emailService;

    @Mock
    private RedisTemplate redisTemplate;

    private Member newMember;
    private Member member;

    private static MockedStatic<CodeGenerator> mockedCodeGenerator;


    @BeforeAll
    public static void beforeClass() {
        mockedCodeGenerator = mockStatic(CodeGenerator.class);
    }

    @AfterAll
    public static void afterClass() {
        mockedCodeGenerator.close();
    }

    @BeforeEach
    public void init() {
        newMember = Member.builder()
                          .memberId("qwerty12345")
                          .password("password")
                          .email("email@gmail.com1")
                          .nickname("Michael1")
                          .positionsId(1)
                          .addressId(1)
                          .build();

        member = Member.builder()
                       .id(1)
                       .memberId("qwerty12345")
                       .password("password")
                       .email("email@gmail.com1")
                       .nickname("Michael1")
                       .positionsId(1)
                       .addressId(1)
                       .point(10000)
                       .build();
    }

    @Test
    @DisplayName("정상적인 회원가입")
    void 정상_회원가입() {
        when(memberService.isMemberIdExist(newMember.getMemberId())).thenReturn(false);
        when(memberService.isNicknameExist(newMember.getNickname())).thenReturn(false);
        when(memberService.isEmailExist(newMember.getEmail())).thenReturn(false);

        memberService.signUp(newMember);

        verify(memberMapper).insert(any(Member.class));
    }

    @Test
    @DisplayName("null로 회원가입")
    void null_회원가입() {
        assertThrows(BadRequestException.class, () -> memberService.signUp(null));
    }

    @Test
    @DisplayName("중복된 아이디 회원가입")
    void 중복된_아이디_회원가입() {
        when(memberService.isMemberIdExist(newMember.getMemberId())).thenReturn(true);

        assertThrows(DuplicatedException.class, () -> memberService.signUp(newMember));
    }

    @Test
    @DisplayName("중복된 닉네임 회원가입")
    void 중복된_닉네임_회원가입() {
        when(memberService.isMemberIdExist(newMember.getMemberId())).thenReturn(false);
        when(memberService.isNicknameExist(newMember.getNickname())).thenReturn(true);

        assertThrows(DuplicatedException.class, () -> memberService.signUp(newMember));
    }

    @Test
    @DisplayName("중복된 이메일 회원가입")
    void 중복된_이메일_회원가입() {
        when(memberService.isMemberIdExist(newMember.getMemberId())).thenReturn(false);
        when(memberService.isNicknameExist(newMember.getNickname())).thenReturn(false);
        when(memberService.isEmailExist(newMember.getEmail())).thenReturn(true);

        assertThrows(DuplicatedException.class, () -> memberService.signUp(newMember));
    }

    @Test
    @DisplayName("회원 삭제")
    void 회원삭제() {
        when(memberMapper.getMemberById(member.getId())).thenReturn(member);

        memberService.deleteAccount(member.getId());

        verify(memberMapper).delete(member.getId());
    }

    @Test
    @DisplayName("null 회원 삭제")
    void null_회원삭제() {
        when(memberMapper.getMemberById(member.getId())).thenReturn(null);

        assertThrows(BadRequestException.class, () -> memberService.deleteAccount(member.getId()));
    }

    @Test
    @DisplayName("회원 영구 삭제")
    void 회원영구삭제() {
        memberService.deletePermanently(member.getId());

        verify(memberMapper).deletePermanently(member.getId());
    }

    @Test
    @DisplayName("id를 통해 member 조회")
    void id로_member_조회() {
        when(memberMapper.getMemberById(member.getId())).thenReturn(member);

        Member testMember = memberService.getMemberById(member.getId());

        assertEquals(testMember, member);
    }

    @Test
    @DisplayName("memberId를 통해 member 조회")
    void memberId로_MEMBER_조회() {
        when(memberMapper.getMemberByMemberId(member.getMemberId())).thenReturn(member);

        Member testMember = memberService.getMemberByMemberId(member.getMemberId());

        assertEquals(testMember, member);
    }

    @Test
    @DisplayName("이메일을 통해 member 조회")
    void 이메일로_MEMBER_조회() {
        when(memberMapper.getMemberByEmail(member.getEmail())).thenReturn(member);

        Member testMember = memberService.getMemberByEmail(member.getEmail());

        assertEquals(testMember, member);
    }

    @Test
    @DisplayName("memberId와 비밀번호로 member 조회")
    void memberId_비밀번호로_member_조회() {
        when(memberMapper.isMemberIdExist(member.getMemberId())).thenReturn(true);
        when(memberMapper.getMemberByMemberId(member.getMemberId())).thenReturn(member);
        when(encryptService.checkPassword(any(), any())).thenReturn(true);

        Optional<Member> optionalMember =
                memberService.getMemberByMemberIdAndPassword(member.getMemberId(), member.getPassword());

        assertEquals(optionalMember.get(), member);
    }

    @Test
    @DisplayName("닉네임 변경")
    void 닉네임_변경() {
        String newNickname = "홍길동";
        when(memberMapper.isNicknameExist(newNickname)).thenReturn(false);

        memberService.updateNickname(member.getId(), newNickname);

        verify(memberMapper).updateNickname(member.getId(), newNickname);
    }

    @Test
    @DisplayName("중복된 닉네임 변경")
    void 중복된_닉네임_변경() {
        String newNickname = "홍길동";
        when(memberMapper.isNicknameExist(newNickname)).thenReturn(true);

        assertThrows(DuplicatedException.class,
                () -> memberService.updateNickname(member.getId(), newNickname));
    }

    @Test
    @DisplayName("비밀번호 변경")
    void 비밀번호_변경() {
        String newPassword = "newPassword";
        when(memberMapper.getMemberById(member.getId())).thenReturn(member);
        when(encryptService.checkPassword(any(), any())).thenReturn(false);
        when(encryptService.encrypt(newPassword)).thenReturn(newPassword);

        memberService.updatePassword(member.getId(), newPassword);

        verify(memberMapper).updatePassword(anyInt(), anyString());
    }

    @Test
    @DisplayName("잘못된 id의 비밀번호 변경")
    void 비밀번호_변경_잘못된id() {
        String newPassword = "newPassword";
        when(memberMapper.getMemberById(member.getId())).thenReturn(null);

        assertThrows(BadRequestException.class,
                () -> memberService.updatePassword(member.getId(), newPassword));
    }

    @Test
    @DisplayName("현재 비밀번호로 비밀번호 변경")
    void 비밀번호_변경_동일한비밃번호() {
        String newPassword = "newPassword";
        when(memberMapper.getMemberById(member.getId())).thenReturn(member);
        when(encryptService.checkPassword(any(), any())).thenReturn(true);

        assertThrows(DuplicatedException.class,
                () -> memberService.updatePassword(member.getId(), newPassword));
    }

    @Test
    @DisplayName("비밀번호 변경폼으로 비밀번호 변경")
    void 비밀번호_변경_UpdatedPasswordRequest() {
        UpdatePasswordRequest updatePasswordRequest =
                new UpdatePasswordRequest("beforePassword", "afterPassword");
        when(memberMapper.getMemberById(member.getId())).thenReturn(member);
        when(encryptService.checkPassword(any(), any())).thenReturn(false);
        when(encryptService.encrypt(any())).thenReturn("newPassword");

        memberService.updatePasswordByRequest(member.getId(), updatePasswordRequest);

        verify(memberMapper).updatePassword(anyInt(), anyString());
    }

    @Test
    @DisplayName("잘못된 id가 입력된 비밀번호 변경폼으로 비밀번호 변경")
    void 비밀번호_변경_UpdatedPasswordRequest_잘못된id() {
        int id = 1;
        UpdatePasswordRequest updatePasswordRequest =
                new UpdatePasswordRequest("samePassword", "afterPassword");
        when(memberMapper.getMemberById(id)).thenReturn(null);

        assertThrows(BadRequestException.class,
                () -> memberService.updatePasswordByRequest(id, updatePasswordRequest));
    }

    @Test
    @DisplayName("비밀번호 변경폼의 before after가 동일한 상태에서 비밀번호 변경")
    void 비밀번호_변경_UpdatedPasswordRequest_동일한비밀번호() {
        UpdatePasswordRequest updatePasswordRequest =
                new UpdatePasswordRequest("samePassword", "samePassword");
        when(memberMapper.getMemberById(member.getId())).thenReturn(member);

        assertThrows(BadRequestException.class,
                () -> memberService.updatePasswordByRequest(member.getId(), updatePasswordRequest));
    }

    @Test
    @DisplayName("현재 사용중인 비밀번호로 변경")
    void 비밀번호_변경_UpdatedPasswordRequest_현재비밀번호로변경() {
        UpdatePasswordRequest updatePasswordRequest =
                new UpdatePasswordRequest("samePassword", "afterPassword");
        when(memberMapper.getMemberById(member.getId())).thenReturn(member);
        when(encryptService.checkPassword(anyString(), anyString())).thenReturn(true);

        assertThrows(DuplicatedException.class,
                () -> memberService.updatePasswordByRequest(member.getId(), updatePasswordRequest));
    }

    @Test
    @DisplayName("포인트 증가")
    void 포인트_증가() {
        when(memberMapper.getMemberById(member.getId())).thenReturn(member);

        memberService.increasePoint(member.getId(), 10);

        verify(memberMapper).increasePoint(member.getId(), 10);
    }

    @Test
    @DisplayName("잘못된 id의 포인트 증가")
    void 포인트_증가_잘못된id() {
        when(memberMapper.getMemberById(member.getId())).thenReturn(null);

        assertThrows(BadRequestException.class,
                () -> memberService.increasePoint(member.getId(), 10));
    }

    @Test
    @DisplayName("1만큼 포인트 증가")
    void 포인트_증가_1만큼() {
        when(memberMapper.getMemberById(member.getId())).thenReturn(member);

        memberService.increasePoint(member.getId(), 1);

        verify(memberMapper).increasePoint(member.getId(), 1);
    }

    @Test
    @DisplayName("0만큼 포인트 증가")
    void 포인트_증가_0만큼() {
        when(memberMapper.getMemberById(member.getId())).thenReturn(member);

        assertThrows(BadRequestException.class,
                () -> memberService.increasePoint(member.getId(), 0));
    }

    @Test
    @DisplayName("포인트 감소")
    void 포인트_감소() {
        when(memberMapper.getMemberById(member.getId())).thenReturn(member);

        memberService.decreasePoint(member.getId(), 10);

        verify(memberMapper).decreasePoint(member.getId(), 10);
    }

    @Test
    @DisplayName("잘못된 id의 포인트 감소")
    void 포인트_감소_잘못된id() {
        when(memberMapper.getMemberById(member.getId())).thenReturn(null);

        assertThrows(BadRequestException.class,
                () -> memberService.decreasePoint(member.getId(), 10));
    }

    @Test
    @DisplayName("보유 포인트와 동일한 포인트 감소")
    void 포인트_감소_보유포인트만큼() {
        Member normalMember = Member.builder()
                                    .id(2)
                                    .point(100)
                                    .build();

        when(memberMapper.getMemberById(normalMember.getId())).thenReturn(normalMember);

        memberService.decreasePoint(normalMember.getId(), 100);

        verify(memberMapper).decreasePoint(normalMember.getId(), 100);
    }

    @Test
    @DisplayName("보유 포인트보다 더 많은 포인트 감소")
    void 포인트_감소_보유포인트보다많이() {
        Member poorMember = Member.builder()
                                  .id(2)
                                  .point(100)
                                  .build();

        when(memberMapper.getMemberById(poorMember.getId())).thenReturn(poorMember);

        assertThrows(BadRequestException.class,
                () -> memberService.decreasePoint(poorMember.getId(), 101));
    }
}