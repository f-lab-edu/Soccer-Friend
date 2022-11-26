package soccerfriend.authentication;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 현재 로그인한 member가 PathVariable에 존재하는 clubId의 운영진인지 확인합니다.
 * PathVaribale로 clubId를 제공받아야합니다.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IsClubLeaderOrManager {
}