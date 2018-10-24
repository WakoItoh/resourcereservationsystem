package rrs.handler;

public class MessageHolder {

    public static final String PM_AUTH_001 = "ログアウトしました。";

    public static final String PM_RSC_001 = "リソースの登録が完了しました。";
    public static final String PM_RSC_002 = "リソースの変更が完了しました。";
    public static final String PM_RSC_003 = "リソースの削除が完了しました。";

    public static final String EM_AUTH_001 = "ユーザIDは必須入力です。";
    public static final String EM_AUTH_002 = "パスワードは必須入力です。";
    public static final String EM_AUTH_003 = "認証に失敗しました。";

    public static final String EM_RSV_001 = "";

    public static final String EM_RSC_001 = "リソースが存在しないか、閲覧を許可されていません。";
    public static final String EM_RSC_002 = "リソースが存在しないか、削除済みのため変更できません。";
    public static final String EM_RSC_003 = "リソース名は必須入力です。";
    public static final String EM_RSC_004 = "リソース名は30文字以内で入力してください。";
    public static final String EM_RSC_005 = "カテゴリは必須入力です。";
    public static final String EM_RSC_006 = "カテゴリを正しく選択してください。";
    public static final String EM_RSC_007 = "定員は必須入力です。";
    public static final String EM_RSC_008 = "定員は半角数字で入力してください。";
    public static final String EM_RSC_009 = "定員は1～999または0で入力してください。";
    public static final String EM_RSC_010 = "事業所は必須入力です。";
    public static final String EM_RSC_011 = "事業所を正しく選択してください。";
    public static final String EM_RSC_012 = "特性を正しく選択してください。";
    public static final String EM_RSC_013 = "補足は500文字以内で入力してください。";
    public static final String EM_RSC_014 = "利用停止開始日時には正しい日付を入力してください。";
    public static final String EM_RSC_015 = "利用停止開始日時には正しい時刻を入力してください。";
    public static final String EM_RSC_016 = "利用停止終了日時を入力している場合、利用停止開始日時を入力してください。";
    public static final String EM_RSC_017 = "利用停止終了日時には正しい日付を入力してください。";
    public static final String EM_RSC_018 = "利用停止終了日時には正しい時刻を入力してください。";
    public static final String EM_RSC_019 = "利用停止開始日時を入力している場合、利用停止終了日時を入力してください。";
    public static final String EM_RSC_020 = "利用停止終了日時は利用停止開始日時より後の日時を入力してください。";
    public static final String EM_RSC_021 = "リソースの登録に失敗しました。\n内容を確認し、再度登録を実行してください。";
    public static final String EM_RSC_022 = "リソースの変更に失敗しました。\n内容を確認し、再度変更を実行してください。";
    public static final String EM_RSC_023 = "リソースの削除に失敗しました。\n内容を確認し、再度削除を実行してください。";

    public static final String EM_COMM_001 = "システムエラーが発生しました。\nシステム管理者にお問い合わせください。";
    public static final String EM_COMM_002 = "ログイン情報が失われました。\n再度ログインしてください。";
    public static final String EM_COMM_003 = "検索結果は0件です。";
    public static final String EM_COMM_004 = "利用者権限がありません。";
    public static final String EM_COMM_005 = "管理者権限がありません。";

    private MessageHolder() {
    }
}
