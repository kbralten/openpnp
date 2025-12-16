param(
    [Parameter(Mandatory = $true)]
    [int]$PRNumber
)

# Configurable variables
$UpstreamRepo = "https://github.com/openpnp/openpnp.git"
$ForkBranch = "ff"
$LocalBranch = "pr-$PRNumber"

# Ensure upstream remote exists
if (-not (git remote | Select-String -Pattern "^upstream$")) {
    git remote add upstream $UpstreamRepo
}

# Fetch the PR branch
git fetch upstream pull/$PRNumber/head:$LocalBranch

# Checkout your fork branch
git checkout $ForkBranch

# Merge the PR branch
git merge --no-ff $LocalBranch

# Push to your fork
git push origin $ForkBranch