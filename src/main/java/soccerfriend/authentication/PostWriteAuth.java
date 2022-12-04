package soccerfriend.authentication;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 현재 로그인한 member가 해당 post의 작성 및 수정 권한이 있는지 확인합니다.
 * PathVaribale로 postId를 제공받아야합니다.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PostWriteAuth {
}
