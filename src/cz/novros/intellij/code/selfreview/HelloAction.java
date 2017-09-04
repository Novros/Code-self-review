package cz.novros.intellij.code.selfreview;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

public class HelloAction extends AnAction {
	@Override
	public void actionPerformed(final AnActionEvent anActionEvent) {
		Project project = anActionEvent.getData(PlatformDataKeys.PROJECT);
		Messages.showMessageDialog(project, "Hello world!", "Greeting", Messages.getInformationIcon());
	}
}
