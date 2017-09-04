package cz.novros.intellij.code.selfreview;

import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.project.ProjectManagerAdapter;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;

import org.jetbrains.annotations.NotNull;

import cz.novros.intellij.code.selfreview.controller.ReviewController;
import cz.novros.intellij.code.selfreview.model.Context;
import cz.novros.intellij.code.selfreview.model.state.FirstStep;
import cz.novros.intellij.code.selfreview.view.ReviewPanel;

public class SelfReviewComponent implements ApplicationComponent {

	@NotNull
	@Override
	public String getComponentName() {
		return "Self review helper";
	}

	@Override
	public void disposeComponent() {
//		Messages.showMessageDialog("Dispose component", "Dispose", Messages.getInformationIcon());
	}

	@Override
	public void initComponent() {
//		Messages.showMessageDialog("Initialize component", "Init", Messages.getInformationIcon());

		ProjectManager.getInstance().addProjectManagerListener(new ProjectManagerAdapter() {
			final Key viewKey = new Key("code-self-review-sid");

			public void projectOpened(final Project project) {
				final ToolWindowManager twm = ToolWindowManager.getInstance(project);

				final Runnable task1 = () -> {
					final ReviewPanel view = getView();

					final ToolWindow toolWindow = twm.registerToolWindow("Code self review", true, ToolWindowAnchor.RIGHT);
					final ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
					final Content content = contentFactory.createContent(view.getBase(), "", false);
					toolWindow.getContentManager().addContent(content);
//						toolWindow.setIcon(new ImageIcon(getClass().getClassLoader().getResource("resources/quicknotes.png")));
					project.putUserData(viewKey, view);
				};
				twm.invokeLater(task1);
			}

			public void projectClosed(final Project project) {
			}
		});
	}

	private ReviewPanel getView() {
		final Context context = new Context(new FirstStep());
		final ReviewController controller = new ReviewController(context);
		final ReviewPanel reviewPanel = new ReviewPanel(controller);
		return reviewPanel;
	}
}
