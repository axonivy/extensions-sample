package ch.ivyteam.ivy.example.process.element.extensions;

import ch.ivyteam.ivy.designer.process.ui.editor.palette.IIvyProcessPalette;
import ch.ivyteam.ivy.designer.process.ui.editor.palette.IIvyProcessPaletteExtension;

/** 
 * Process Palette Extension for My Own Process Elements
 * @author rwei
 * @since 20.08.2008
 */
public class MyOwnProcessPaletteExtension implements IIvyProcessPaletteExtension
{
  /** The name of the myOwn palette group */
  private static final String MYOWN_GROUP = "ch.ivyteam.ivy.example.process.element.extensions.MyOwnGroup";

  /**
   * @see ch.ivyteam.ivy.designer.process.ui.editor.palette.IIvyProcessPaletteExtension#addEntries(ch.ivyteam.ivy.designer.process.ui.editor.palette.IIvyProcessPalette)
   */
  @Override
  public void addEntries(IIvyProcessPalette palette)
  {
    palette.addProcessElementEntry(MYOWN_GROUP, "MyOwnStep", 1000);
    palette.addProcessElementEntry(MYOWN_GROUP, "MyOwnStartEvent", 2000);
    palette.addProcessElementEntry(MYOWN_GROUP, "MyOwnIntermediateEvent", 3000);
    palette.addProcessElementEntry(MYOWN_GROUP, "MyOwnProgramUserInterface", 4000);
    palette.addProcessElementEntry(MYOWN_GROUP, "MyOwnCallAndWait", 6000);
  }

  /**
   * @see ch.ivyteam.ivy.designer.process.ui.editor.palette.IIvyProcessPaletteExtension#addGroups(ch.ivyteam.ivy.designer.process.ui.editor.palette.IIvyProcessPalette)
   */
  @Override
  public void addGroups(IIvyProcessPalette palette)
  {
    palette.addGroup(MYOWN_GROUP, "MyOwn", 10000);
  }

}
