# PROJECT TITLE:
StudyTrack — Student Task & Deadline Manager

# TEAM MEMBERS:
Melwin S. Recalde - melwinrecalde

# PROBLEM STATEMENT & GOALS:
The students find themselves handling more than one assignment, quiz, project and other academic related issues at a time, which makes it quite challenging for them to track all these things.

StudyTrack tries to offer a central platform for the organization and tracking of all academic related tasks by the students. The main objectives of this platform are to help users in creating, editing, deleting, prioritizing and completion of tasks.

# TARGET USER:
Students in colleges that require a simplistic means of managing their academic needs.

# BRIEF DESCRIPTION:
StudyTrack is an application which enables users to organize their assignments, tests, and projects on one platform.

The user can create academic tasks, allocate subject, deadlines, priority and status for each task. They will be able to view, edit, delete, search and filter their tasks. The application will also show the upcoming and overdue tasks and give a dashboard with the user's total workload.

# CORE OOP CONCEPTS:
- Encapsulation: Task, User, and Subject data will use private attributes with getter and setter methods to control access and validate information.
- Inheritance: AssignmentTask, QuizTask, and ProjectTask will inherit common properties and behaviors from the abstract Task class.
- Polymorphism: Different task types will be treated as Task objects while providing task-specific implementations of shared methods such as getTaskType().
- Abstraction: The Task class will be abstract and will define common task behavior, while the Prioritizable interface will define priority-related operations.

# INITIAL CLASS IDEAS:
- Task: Abstract class responsible for storing common task information such as title, description, due date, priority, and status.
- AssignmentTask: Represents academic assignments and laboratory activities.
- QuizTask: Represents quizzes and examinations.
- ProjectTask: Represents larger academic projects.
- User: Represents the student using the system.
- Subject: Represents an academic subject associated with tasks.
- TaskManager: Manages task creation, editing, deletion, searching, filtering, and retrieval.
- Prioritizable: Interface responsible for defining priority-related behavior.

# USER STORIES:
- As a student, I want to create an academic task in order to monitor my requirements.
- As a student, I want to create a deadline for an academic task in order to know when it is due.
- As a student, I want to assign a priority to an academic task in order to understand which tasks I should start from.
- As a student, I want to categorize my academic tasks by subject in order to manage different requirements.
- As a student, I want to mark an academic task as complete in order to monitor my progress.
- As a student, I want to see my overdue tasks in order to pay attention to them.
- As a student, I want to find a particular requirement using the search tool in order to save time.
- As a student, I want my tasks to be stored in the application in order not to lose my information.

# CORE FEATURES :
- Academic tasks can be created, edited, and deleted.
- Can accommodate various types of academic tasks such as assignments, quizzes, and projects.
- Set deadlines and priorities for the tasks.
- Tasks organized according to subjects.
- Pending, in progress, and completed tasks marked accordingly.
- Identify future and past due tasks.
- Search and filter tasks.
- Show academic workload dashboard.
- Store and retrieve task data through File I/O operations.
- Validate user inputs.
- GUI using JavaFX.
