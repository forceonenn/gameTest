# TestingGame

## Shortly about the project
The project required parameters --config and --betting-amount.
I've used this libraries:
1. common-cli for quick arguments parsing
2. gson for serialize and deserialize json
3. junit for testing

### What have I have done
I implemented everything that has been presented in the task description, 
including all optional requirements.
You can find several json configurations here:
1. config.json - the original file from task description. By the way there is a mistyping in "same_symbols_horizontally" combination. You can find two "1:1" strings, but one of them should be "1:2". Of course the program had to work successfully following this map, but the result hasn't been logical in this case. I've fixed it in my project, I hope it will help you also in the future.
2. config4x4.json - just for making sure that the program works successfully with another configs. All the neccessary lines (such as symbols probabilities, covered areas for new zones, etc.) are added there. I didn't implement a check if all the parameters are presented in the json file for required matrix. You know, it's not difficult to do, but I believe, it's out of this task.
3. configTest.json - just for passing a unit tests. Just a copy of config.json at the moment.

### About what I didn't find in the task description
1. Tests. There was nothing about it in requirements, so I've just added two simple tests just as example.
2. Bonus symbol possibility. I created my own method on my own terms, there's an unnecessary call of Math.random(), but while I don't push it in the production, I found this way funny somewhy :)
3. Output. Actual json format is presented in the description. But I found no information, what I have to do with it. So at the moment I just print it by System.out.println() and that's all.

### Summary

Without jokes, it's a really interesting task, you did it very well.
I hope you'll be satisfied with my solution.