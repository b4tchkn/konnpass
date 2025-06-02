module.exports = async ({ github, context, core }) => {
    const currentVersion = context.payload.pull_request?.head?.ref?.split('/')[1];
    const nextVersion = currentVersion?.replace(/v(\d+)\.(\d+)\.(\d+)/, (_, major, minor, patch) => {
        return `v${major}.${Number(minor) + 1}.${patch}`;
    });
    if (!nextVersion) {
        core.setFailed("Could not determine next version from the current branch name.");
        return;
    }
    await github.rest.issues.createMilestone({
        owner: context.repo.owner,
        repo: context.repo.repo,
        title: nextVersion,
    });
}
