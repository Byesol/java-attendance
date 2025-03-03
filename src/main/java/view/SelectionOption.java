package view;

import java.util.Arrays;

public enum SelectionOption {
    ADD_ATTENDANCE("1"),
    EDIT_ATTENDANCE("2"),
    GET_ATTENDANCE_HISTORY("3"),
    CHECK_ABSENCE_USERS("4"),
    QUIT("Q");

    private final String option;

    SelectionOption(String option) {
        this.option = option;
    }

    public static SelectionOption getSelectOption(String input) {
        return Arrays.stream(SelectionOption.values())
                .filter(select -> select.option.equals(input))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format(
                                "[ERROR] 잘못된 입력입니다. 입력 가능한 옵션을 입력해 주세요.  입력 가능한 옵션: 1(출석 추가), 2(출석 수정), 3(크루별 출석 기록 확인), 4(제적 위험자 조회), Q(종료)")
                ));
    }

}
