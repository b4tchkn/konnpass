module.exports = async ({ github, context, core }) => {
    const currentVersion = context.ref.split('/')[1];
    const nextVersion = currentVersion.replace(/v(\d+)\.(\d+)\.(\d+)/, (_, major, minor, patch) => {
        return `v${major}.${Number(minor) + 1}.${patch}`;
    });
    await github.rest.issues.createMilestone({
        owner: context.repo.owner,
        repo: context.repo.repo,
        title: nextVersion,
    });
}
