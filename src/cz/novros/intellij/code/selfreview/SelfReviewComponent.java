package cz.novros.intellij.code.selfreview;

import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.project.VetoableProjectManagerListener;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;

import org.jetbrains.annotations.NotNull;

import cz.novros.intellij.code.selfreview.controller.SaveStateService;
import cz.novros.intellij.code.selfreview.controller.ReviewController;
import cz.novros.intellij.code.selfreview.view.ReviewPanel;

public class SelfReviewComponent implements ApplicationComponent {

	@NotNull
	@Override
	public String getComponentName() {
		return "Self review helper";
	}

	@Override
	public void disposeComponent() {
	}

	@Override
	public void initComponent() {
		ProjectManager.getInstance().addProjectManagerListener(new VetoableProjectManagerListener() {
			@Override
			public boolean canClose(@NotNull final Project project) {
				return true;
			}

			public void projectOpened(final Project project) {
				final SaveStateService originator = ServiceManager.getService(project, SaveStateService.class);

				final ToolWindowManager twm = ToolWindowManager.getInstance(project);

				final Runnable task1 = () -> {
					final ToolWindow toolWindow = twm.registerToolWindow("Code self review", true, ToolWindowAnchor.RIGHT);
					final ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
					final Content content = contentFactory.createContent(getView(originator).getBase(), "", false);

					toolWindow.getContentManager().addContent(content);
//						toolWindow.setIcon(new ImageIcon(getClass().getClassLoader().getResource("resources/quicknotes.png")));
				};
				twm.invokeLater(task1);
			}

			public void projectClosed(final Project project) {
			}
		});
	}

	public ReviewPanel getView(@NotNull final SaveStateService originator) {
		final ReviewController controller = new ReviewController();
		controller.setContext(originator.getContext());

		return new ReviewPanel(controller);
	}
}
