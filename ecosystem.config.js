module.exports = {
  apps: [
    {
      name: 'Timesheet', // Replace with your app name
      script: './gradlew',
      args: 'bootRun',
      interpreter: 'bash', // Use bash to execute the script
      exec_mode: 'fork', // Use fork mode
    },
  ],
};
