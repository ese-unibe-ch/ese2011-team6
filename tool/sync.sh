#!/bin/sh -e

#m_mutt=$(which mutt)
m_addr=ese-team5@iam.unibe.ch

m_safe=$1
m_done=
m_temp=$(mktemp)
m_master=master
m_slaves="master-model master-test master-view"

m_backup ()
{
	test -z "$m_safe" &&
		return

	test -d .git_orig &&
		rm -rf .git_orig
	test -d .git &&
		cp -R .git .git_orig
}

m_restore ()
{
	test -z "$m_safe" &&
		return

	test -d .git_old &&
		rm -rf .git_old
	test -d .git && test -d .git_orig &&
		mv .git .git_old &&
		mv .git_orig .git

	echo '
	Repository successfully restored.
	Working directory dirty?  Try "git reset --hard".'
}

m_fetch ()
{
	git fetch --all
}

m_pull ()
{
	git checkout $1
	git pull
}

m_merge_up ()
{
	git checkout $m_master
	git merge $1
}

m_merge_down ()
{
	git checkout $1
	git merge $m_master
}

m_push ()
{
	git push --all
}

m_main ()
{
	m_fetch
	for i in $m_master $m_slaves; do
		m_pull $i
	done
	for i in $m_slaves; do
		m_merge_up $i
	done
	for i in $m_slaves; do
		m_merge_down $i
	done
	m_push
	>$m_temp.done # pipefail not available
}

trap 'test $? != 0 && m_restore' EXIT
m_backup

m_main | tee $m_temp

#test -n $m_mutt &&
#	test -f $m_temp.done &&
#	mutt -s "git-sync: $(date)" -- $m_addr <$m_temp
