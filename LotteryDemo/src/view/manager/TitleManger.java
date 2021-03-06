package view.manager;

import java.util.Observable;
import java.util.Observer;

import org.apache.commons.lang3.StringUtils;

import view.LoginUI;
import view.manager.deprecated.SecondUI;
import jamffy.example.lotterydemo.ConstantValues;
import jamffy.example.lotterydemo.GlobalParams;
import jamffy.example.lotterydemo.R;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 管理界面的标题栏 找上面的控件，提供多种显示样式
 * 
 * @author tmac
 *
 */
public class TitleManger implements Observer {
	// 单例设计模式
	private static TitleManger titleManger = new TitleManger();

	private TitleManger() {
		super();
	}

	public static TitleManger getInstance() {
		return titleManger;
	}

	private RelativeLayout commonContainer;
	private RelativeLayout loginContainer;
	private RelativeLayout unLoginContainer;

	private ImageView goback;// 返回
	private ImageView help;// 帮助
	private ImageView login;// 登录

	private TextView titleContent;// 标题内容
	private TextView userInfo;// 用户信息

	public void init(Activity activity) {
		commonContainer = (RelativeLayout) activity
				.findViewById(R.id.ii_common_container);
		unLoginContainer = (RelativeLayout) activity
				.findViewById(R.id.ii_unlogin_title);
		loginContainer = (RelativeLayout) activity
				.findViewById(R.id.ii_login_title);

		goback = (ImageView) activity.findViewById(R.id.ii_title_goback);
		help = (ImageView) activity.findViewById(R.id.ii_title_help);
		login = (ImageView) activity.findViewById(R.id.ii_title_login);

		titleContent = (TextView) activity.findViewById(R.id.ii_title_content);
		userInfo = (TextView) activity.findViewById(R.id.ii_top_user_info);

		setListener();
	}

	private void setListener() {
		goback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				System.out.println("返回键");
				MiddleManager.getInstance().goBack();

			}
		});
		help.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				System.out.println("help");

			}
		});
		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				System.out.println("login");

				// MiddleManager mm = MiddleManager.getInstance();
				// mm.changeUI(new SecondUI(mm.getContext()));

				// changeUI需要修改，不能传递对象，但是明确目标
				// 解决频繁点击造成的频繁构造对象问题
				MiddleManager mm = MiddleManager.getInstance();
				mm.changeUI(LoginUI.class);
			}
		});
	}

	private void initTitle() {
		commonContainer.setVisibility(View.GONE);
		loginContainer.setVisibility(View.GONE);
		unLoginContainer.setVisibility(View.GONE);
	}

	/**
	 * 显示通用标题
	 */
	public void showCommonTitle() {
		initTitle();
		commonContainer.setVisibility(View.VISIBLE);
	}

	/**
	 * 显示未登录的标题
	 */
	public void showUnLoginTitle() {
		initTitle();
		unLoginContainer.setVisibility(View.VISIBLE);
	}

	/**
	 * 显示登陆的标题
	 */
	public void showLoginTitle() {
		initTitle();
		loginContainer.setVisibility(View.VISIBLE);

	}

	public void changTitleContent(String text) {
		titleContent.setText(text);
	}

	@Override
	public void update(Observable observable, Object data) {
		if (observable instanceof MiddleManager) {

			if (data != null && StringUtils.isNumeric(data.toString())) {
				int id = Integer.parseInt(data.toString());
				switch (id) {
				case ConstantValues.VIEW_FIRST:
					showUnLoginTitle();
					break;
				case ConstantValues.VIEW_SECOND:
				case ConstantValues.VIEW_SSQ:
				case ConstantValues.VIEW_SHOPPING:
				case ConstantValues.VIEW_LOGIN:
					showCommonTitle();
					break;
				case ConstantValues.VIEW_HALL:
					if (GlobalParams.isLogined) {
						showLoginTitle();
						String text = "用户：" + GlobalParams.USER_NAME + "\n"
								+ "余额：" + GlobalParams.USER_BALANCE.toString()
								+ "元";
						userInfo.setText(text);
					} else {
						showUnLoginTitle();
					}
					break;
				default:
					break;
				}
			}

		}
	}

}
