import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ToDoListGUI extends JFrame {
    // window size constraints
    public static final int FRAME_WIDTH = 540;
    public static final int FRAME_HEIGHT = 760;
    
    // Components
    private JPanel mainPanel;
    private JPanel taskPanel;
    private JLabel bannerLabel;
    private JTextField taskInputField;
    private JButton addTaskButton;
    private JScrollPane taskScrollPane;  
    private JScrollPane finishedScrollPane; 
    private JPanel finishedPanel;
    
    public ToDoListGUI() {
        // Set up frame
        setTitle("My To-Do List Program");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window on the screen
        
        // Initialize components
        guiComponents();
        
        // Make the window visible
        setVisible(true);
    }
    
    private void guiComponents() {
        // Main panel with BorderLayout for positioning to organize header, center, and footer
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        setContentPane(mainPanel);
    
        // Title banner at the top
        bannerLabel = new JLabel("To Do List", SwingConstants.CENTER);
        bannerLabel.setFont(new Font("Arial", Font.BOLD, 28));
        mainPanel.add(bannerLabel, BorderLayout.NORTH);
    
        // Panel for tasks (main to-do list)
        taskPanel = new JPanel();
        taskPanel.setLayout(new BoxLayout(taskPanel, BoxLayout.Y_AXIS));
        taskPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
        // Scroll pane for task panel
        taskScrollPane = new JScrollPane(taskPanel);
        taskScrollPane.setPreferredSize(new Dimension(FRAME_WIDTH - 60, 320));
        taskScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        taskScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    
        // titled border for "Tasks"
        JLabel taskTitle = new JLabel("Tasks");
        taskTitle.setFont(new Font("Arial", Font.BOLD, 18));
        taskTitle.setAlignmentX(Component.CENTER_ALIGNMENT);  // center-align label
    
        // Finished tasks panel
        finishedPanel = new JPanel();
        finishedPanel.setLayout(new BoxLayout(finishedPanel, BoxLayout.Y_AXIS));
        finishedPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
        // Scroll pane for finished tasks
        finishedScrollPane = new JScrollPane(finishedPanel);
        finishedScrollPane.setPreferredSize(new Dimension(FRAME_WIDTH - 60, 220));
        finishedScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        finishedScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    
        // titled border for "Finished Tasks"
        JLabel finishedTitle = new JLabel("Finished Tasks");
        finishedTitle.setFont(new Font("Arial", Font.BOLD, 18));
        finishedTitle.setAlignmentX(Component.CENTER_ALIGNMENT);  // center-align label
    
        // Combine both scroll panes into the center of the window
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
        // labels above scroll panes instead of using TitledBorder
        centerPanel.add(taskTitle);
        centerPanel.add(taskScrollPane);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20))); // spacing
        centerPanel.add(finishedTitle);
        centerPanel.add(finishedScrollPane);
    
        mainPanel.add(centerPanel, BorderLayout.CENTER);
    
        // Bottom panel for input + add button
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
    
        taskInputField = new JTextField();
        taskInputField.setFont(new Font("Arial", Font.PLAIN, 18));
        taskInputField.setPreferredSize(new Dimension(0, 45));
        bottomPanel.add(taskInputField, BorderLayout.CENTER);
    
        addTaskButton = new JButton("Add Task");
        addTaskButton.setFont(new Font("Arial", Font.BOLD, 18));
        addTaskButton.setPreferredSize(new Dimension(130, 45));
        bottomPanel.add(addTaskButton, BorderLayout.EAST);
    
        addTaskButton.addActionListener(e -> addTask());
        taskInputField.addActionListener(e -> addTask());
    
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    }
    
    
    
    
    
    private void addTask() {
        String taskText = taskInputField.getText().trim();
        if (!taskText.isEmpty()) {
            // Create a task panel with the task text and buttons
            JPanel taskItemPanel = createTaskPanel(taskText, false);  // Add false parameter for active task
            
            // Add to the task panel
            taskPanel.add(taskItemPanel);
            taskPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Spacing between tasks
            
            // Clear input field
            taskInputField.setText("");
            
            // Update UI
            updateUI(taskScrollPane);  // Pass the correct scroll pane
            
            // Set focus back to input field
            taskInputField.requestFocusInWindow();
        }
    }
    
    private JPanel createTaskPanel(String text, boolean isFinished) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        panel.setMaximumSize(new Dimension(FRAME_WIDTH - 80, 40));  // Adjusted width
        panel.setPreferredSize(new Dimension(FRAME_WIDTH - 80, 40));  // Set preferred size
        panel.setBackground(Color.WHITE); // default background
        
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                panel.setBackground(new Color(230, 230, 255)); // light blue hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panel.setBackground(Color.WHITE); // back to normal
            }
        });
        
        // Task label
        JLabel taskLabel = new JLabel(text);
        taskLabel.setFont(new Font("Arial", Font.PLAIN, 16));  // Reduced font size
        taskLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
        
        // Strike through text if it's a finished task
        if (isFinished) {
            taskLabel.setText("<html><strike>" + text + "</strike></html>");
        }
        
        panel.add(taskLabel, BorderLayout.CENTER);
        
        // Buttons container
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 5, 0));

        // Done button (only show in active tasks)
        if (!isFinished) {
            JButton doneButton = new JButton("✔");
            doneButton.setFont(new Font("Arial", Font.BOLD, 12));
            doneButton.setFocusPainted(false);
            doneButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Remove from active tasks
                    int index = -1;
                    for (int i = 0; i < taskPanel.getComponentCount(); i++) {
                        if (taskPanel.getComponent(i) == panel) {
                            index = i;
                            break;
                        }
                    }
                    
                    if (index != -1) {
                        taskPanel.remove(index); // Remove task panel
                        if (index < taskPanel.getComponentCount() && taskPanel.getComponent(index) instanceof Box.Filler) {
                            taskPanel.remove(index); // Remove spacer
                        }
                    }
                    
                    // Create new panel with strikethrough text for finished tasks
                    JPanel finishedItemPanel = createTaskPanel(text, true);
                    
                    // Add to finished panel
                    finishedPanel.add(finishedItemPanel);
                    finishedPanel.add(Box.createRigidArea(new Dimension(0, 5)));
                    
                    // Update both panels
                    updateUI(taskScrollPane);
                    updateUI(finishedScrollPane);
                }
            });
            buttonPanel.add(doneButton);
        }

        // Delete button
        JButton removeButton = new JButton("✕");
        removeButton.setFont(new Font("Arial", Font.BOLD, 12));
        removeButton.setFocusPainted(false);
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Container parent = panel.getParent();
                if (parent != null) {
                    int index = -1;
                    for (int i = 0; i < parent.getComponentCount(); i++) {
                        if (parent.getComponent(i) == panel) {
                            index = i;
                            break;
                        }
                    }
                    
                    if (index != -1) {
                        parent.remove(index); // Remove task panel
                        if (index < parent.getComponentCount() && parent.getComponent(index) instanceof Box.Filler) {
                            parent.remove(index); // Remove spacer
                        }
                    }
                    
                    // Update the appropriate panel
                    if (parent == taskPanel) {
                        updateUI(taskScrollPane);
                    } else if (parent == finishedPanel) {
                        updateUI(finishedScrollPane);
                    }
                }
            }
        });
        buttonPanel.add(removeButton);

        panel.add(buttonPanel, BorderLayout.EAST);

        // Make sure this component aligns properly in the BoxLayout
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        return panel;
    }
    
    private void updateUI(JScrollPane scrollPane) {
        // Get the panel within the scroll pane
        Component view = scrollPane.getViewport().getView();
        if (view instanceof JPanel) {
            JPanel panel = (JPanel) view;
            panel.revalidate();
            panel.repaint();
        }
        
        // Update the scroll pane itself
        scrollPane.revalidate();
        scrollPane.repaint();
        
        // Update the main frame
        revalidate();
        repaint();
        
        // Auto-scroll to bottom
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JScrollBar vertical = scrollPane.getVerticalScrollBar();
                vertical.setValue(vertical.getMaximum());
            }
        });
        
    }
    
    // Main method to launch the application
    public static void main(String[] args) {
        // Run the application on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ToDoListGUI();
            }
        });
    }
}