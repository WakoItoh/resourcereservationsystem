package rrs.handler;

public class MessageHolder {

    public static final String PM_AUTH_001 = "ログアウトしました。";

    public static final String PM_RSV_001 = "予約が完了しました。";
    public static final String PM_RSV_002 = "予約の変更が完了しました。";
    public static final String PM_RSV_003 = "予約のキャンセルが完了しました。";

    public static final String PM_RSC_001 = "リソースの登録が完了しました。";
    public static final String PM_RSC_002 = "リソースの変更が完了しました。";
    public static final String PM_RSC_003 = "リソースの削除が完了しました。";
    public static final String PM_RSC_004 = "利用停止しようとしているリソースは既に予約されています。\n利用停止を続行すると以下の予約は強制的に削除されます。";
    public static final String PM_RSC_005 = "削除しようとしているリソースは既に予約されています。\n削除を続行すると以下の予約は強制的に削除されます。";

    public static final String EM_AUTH_001 = "ユーザIDは必須入力です。";
    public static final String EM_AUTH_002 = "パスワードは必須入力です。";
    public static final String EM_AUTH_003 = "認証に失敗しました。";

    public static final String EM_RSV_001 = "予約が存在しません。";
    public static final String EM_RSV_002 = "予約が存在しないか、予約者ではないため変更できません。";
    public static final String EM_RSV_003 = "予約日開始には正しい日付を入力してください。";
    public static final String EM_RSV_004 = "予約日終了には正しい日付を入力してください。";
    public static final String EM_RSV_005 = "予約日終了は予約日開始以降の日付を入力してください。";
    public static final String EM_RSV_006 = "利用日時は必須入力です。";
    public static final String EM_RSV_007 = "利用日には正しい日付を入力してください。";
    public static final String EM_RSV_008 = "利用日は本日以降の日付を入力してください。";
    public static final String EM_RSV_009 = "利用開始時刻には正しい時刻を入力してください。";
    public static final String EM_RSV_010 = "利用終了時刻には正しい時刻を入力してください。";
    public static final String EM_RSV_011 = "利用終了時刻は利用開始時刻より後の時刻を入力してください。";
    public static final String EM_RSV_012 = "利用終了時刻は現在時刻より後の時刻を入力してください。";
    public static final String EM_RSV_013 = "利用時間数は必須入力です。";
    public static final String EM_RSV_014 = "利用時間数には正しい時間を入力してください。";
    public static final String EM_RSV_015 = "利用時間数は利用日時の時間数以内で入力してください。";
    public static final String EM_RSV_016 = "空きリソースを検索し利用日時を指定してください。";
    public static final String EM_RSV_017 = "空きリソースを検索しリソースを指定してください。";
    public static final String EM_RSV_018 = "リソースが存在しないか、削除済みのため予約できません。";
    public static final String EM_RSV_019 = "会議名は必須入力です。";
    public static final String EM_RSV_020 = "会議名は30文字以内で入力してください。";
    public static final String EM_RSV_021 = "予約者は変更不可です。";
    public static final String EM_RSV_022 = "共同予約者を正しく選択してください。";
    public static final String EM_RSV_023 = "利用人数は必須入力です。";
    public static final String EM_RSV_024 = "利用人数は半角数字で入力してください。";
    public static final String EM_RSV_025 = "利用人数は0～999で入力してください。";
    public static final String EM_RSV_026 = "参加者種別を正しく選択してください。";
    public static final String EM_RSV_027 = "補足は500文字以内で入力してください。";
    public static final String EM_RSV_028 = "本日中は今すぐ予約できません。\n通常の予約を実行してください。";
    public static final String EM_RSV_029 = "予約が存在しないためコピーできませんでした。";
    public static final String EM_RSV_030 = "既に予約または利用停止されているため予約が行えませんでした。";
    public static final String EM_RSV_031 = "予約に失敗しました。\n内容を確認し、再度予約を実行してください。";
    public static final String EM_RSV_032 = "予約の変更に失敗しました。\n内容を確認し、再度変更を実行してください。";
    //public static final String EM_RSV_033 = "予約のキャンセルに失敗しました。\n内容を確認し、再度キャンセルを実行してください。";

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
    public static final String EM_RSC_014 = "利用停止開始日には正しい日付を入力してください。";
    public static final String EM_RSC_015 = "利用停止開始時刻には正しい時刻を入力してください。";
    public static final String EM_RSC_016 = "利用停止終了日時を入力している場合、利用停止開始日時を入力してください。";
    public static final String EM_RSC_017 = "利用停止終了日には正しい日付を入力してください。";
    public static final String EM_RSC_018 = "利用停止終了時刻には正しい時刻を入力してください。";
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
