package soccerfriend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soccerfriend.dto.Member;
import soccerfriend.exception.exception.BadRequestException;
import soccerfriend.exception.exception.DuplicatedException;
import soccerfriend.mapper.MemberMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberMapper memberMapper;

    @Mock
    private EncryptService encryptService;

    private Member newMember;
    private Member member;

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
}